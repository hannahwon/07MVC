package com.model2.mvc.service.product;


import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

//==> ªÛ«∞∞¸∏Æø°º≠ CRUD √ﬂªÛ»≠/ƒ∏Ω∂»≠«— DAO Interface Definition
public interface ProductDAO {


	//INSERT
	public void addProduct(Product product) throws Exception;
	
	//SELECT ONE
	public Product getProduct(int prodNo) throws Exception;
	
	//SELECT LIST
	public List<Product> getProductList(Search search) throws Exception;
	
	//UPDATE
	public void updateProduct(Product product) throws Exception;
	
	//∞‘Ω√∆« Page √≥∏Æ∏£ ¿ß«— ¿¸√º Row(totalCount) return
	public int getTotalCount(Search search) throws Exception;
	
	/*public void insertProduct(Product productVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO product VALUES(seq_product_prod_no.nextval,?,?,?,?,?,SYSDATE)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replaceAll("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());

		stmt.executeUpdate();

		System.out.println("insert :" + stmt + "øœ∑·");
		System.out.println(con);

		con.close();

	}

	public Product findProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM PRODUCT WHERE PROD_NO =?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product productVO = null;
		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));

		}
		rs.close();
		con.close();
		stmt.close();

		return productVO;

	}

	public Map<String, Object> getProductList(Search searchVO) throws Exception {

		Map<String , Object>  map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();

		// Original Query Íµ¨ÏÑ±
		String sql = "SELECT * FROM PRODUCT";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")&& !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE PROD_NO LIKE '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1")&& !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE PROD_NAME LIKE '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("2")&& !searchVO.getSearchKeyword().equals("")) {
				sql += " WHERE PRICE LIKE '%" + searchVO.getSearchKeyword() + "%'";

			}
		}
		sql += " order by PROD_NO";

		System.out.println("ProductDAO::Original SQL ::"+sql);

		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
				
		//==> CurrentPage Í≤åÏãúÎ¨ºÎßå Î∞õÎèÑÎ°? Query ?ã§?ãúÍµ¨ÏÑ±
		sql = makeCurrentPageSql(sql, searchVO);
		System.out.println("currentpage query");
		
		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		
		ResultSet rs = stmt.executeQuery();

		System.out.println(searchVO);
		
		List<Product> list = new ArrayList<Product>();
		
		while (rs.next()) {
			Product vo = new Product();
			vo.setProdNo(rs.getInt("prod_no"));
			vo.setProdName(rs.getString("prod_name"));
			vo.setProdDetail(rs.getString("prod_detail"));
			vo.setManuDate(rs.getString("manufacture_day"));
			vo.setPrice(rs.getInt("price"));
			vo.setFileName(rs.getString("image_file"));
			vo.setRegDate(rs.getDate("reg_date"));
			list.add(vo);
			
				
			
		}
			//==> totalCount ?†ïÎ≥? ???û•
			map.put("totalCount", new Integer(totalCount));
			//==> currentPage ?ùò Í≤åÏãúÎ¨? ?†ïÎ≥? Í∞ñÎäî List ???û•
			map.put("list", list);

			rs.close();
			stmt.close();
			con.close();
			
			return map;
	}

	public void updateProduct(Product productVO) throws Exception {

		Connection con = DBUtil.getConnection();
		System.out.println("ProductDAO?óê?Ñú updateProduct ?ãú?ûë : " + con);
		String sql = "update PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replaceAll("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		System.out.println("updateProduct ?ôÑÎ£?");
		stmt.close();
		con.close();
	}
	// Í≤åÏãú?åê Page Ï≤òÎ¶¨Î•? ?úÑ?ïú ?†ÑÏ≤? Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
				
			sql = "SELECT COUNT(*) "+
				       "FROM ( " +sql+ ") countTable";
				
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
				
			int totalCount = 0;
			if( rs.next() ){
					totalCount = rs.getInt(1);
			}
				
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}
		
		// Í≤åÏãú?åê currentPage Row Îß?  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("ProductDAO :: make SQL :: "+ sql);	
			
			return sql;
		}*/
}
