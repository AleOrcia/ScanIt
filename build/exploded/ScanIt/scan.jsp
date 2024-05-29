<%@ page import="ingSw_beans.SessionMap"%>


<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="Expires" content="-1"/>
  <title>ScanIt</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');
  </style>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/quagga/0.12.1/quagga.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
  <script src="scripts/scan.js"></script>
  
</head>

<%
	SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
	
	HttpSession s = request.getSession(false); // Recupero session	
	
	if(sessionMap == null){
		response.sendRedirect("login.html"); 
	}else if (!sessionMap.getDSessions().containsKey(s) || s == null){
		response.sendRedirect("login.html");
	}	
	
%>


<body class="bg-white dark:bg-gray-900">
  <div class="flex flex-col items-center justify-center min-h-screen">
    <div class="w-full max-w-sm mx-auto">
      <div class="flex flex-col items-center justify-center">
      	<img src="images/logo.jpg" alt="logo">
        <!--  <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">Scanlt</h1>-->
        <div class="mt-4 mb-2 space-y-4" style="width: 65%; height: 300px;" id="barcode-scanner">
        </div>
        <div class="mt-4">
        <p class="text-sm text-gray-500 dark:text-gray-400">Premi per scansionare il prodotto</p> 
        </div>
        <div class="mt-4">
          <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" onclick="scanBarcode()">SCANSIONA</button>
        </div>
        <%
        if(sessionMap != null && sessionMap.getDSessions().containsKey(s)){
        %>
           <div hidden="hidden" id="username"><%=sessionMap.getDSessions().get(s).getUsername() %></div>
       
        <%
        }
        %>
      </div>
    </div>
  </div>
</body>
</html>
