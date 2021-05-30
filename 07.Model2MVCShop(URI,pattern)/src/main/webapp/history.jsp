<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>

<title>열어본 상품 보기</title>

</head>
<body>
	당신이 열어본 상품을 알고 있다
<br>
<br>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	Cookie[] cookies = request.getCookies();
	System.out.println("history.jsp에서 cookies.length : "+cookies.length);
	
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			
			//if (cookie.getName().equals("history")) {
			if (cookie.getName() != null && cookie.getName().startsWith("history")) {
			
				history = cookie.getValue();
				
				System.out.println("history.jsp에서 cookie.getName : "+cookie.getName());
				System.out.println("history.jsp에서 history : "+history);
		
				
				%>
				<a href="/product/getProduct?prodNo=<%=history%>&menu=search"  target="rightFrame"><%=history%></a>
				<br>
				<%
			
			}
		}
	}
	
%>

</body>
</html>