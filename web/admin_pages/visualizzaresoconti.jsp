<%@ page import="ingSw_beans.SessionMap"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="Expires" content="-1"/>
  <title>Visualizza Resoconti</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');
  </style>
<link rel="stylesheet" href="../styles/admin.css">
<script src="../scripts/admin.js"></script>

</head>

<%
	SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
	
	HttpSession s = request.getSession(false); // Recupero session	
	
	if(sessionMap == null){
		response.sendRedirect("../login.html"); 
	}else if (!sessionMap.getASessions().containsKey(s) || s == null){
		response.sendRedirect("../login.html");
	}	
	
%>

<body class="bg-white dark:bg-gray-900">
   <div class="container">
       <div class="left-column mt-4">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">Visualizza Resoconti</h1>
           
       </div>
       <div class="right-column">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400" class="text-3xl font-bold text-blue-600 dark:text-blue-400">ScanIt</h1>
           <div class="article mt-4">
               <h2>Visualizza Resoconti</h2>
           </div>
       </div>
   </div>
</body>
</html>