<%@ page import="ingSw_beans.SessionMap"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="Expires" content="-1"/>
  <title>Gestione Magazzino</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');
    
    .right-align {
        justify-self: end;
    }
  </style>
<link rel="stylesheet" href="../styles/admin.css">
<script src="../scripts/admin.js"></script>
<script src="../scripts/utils.js"></script>

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
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">Servizi</h1>

 			<div class="mt-4">
               <button class="w-59 h-12 px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="gp" onclick="redirect(this)">Gestione Personale</button>
           	</div>
           	<div class="mt-4">
               <button class="w-59 h-12 px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="gm" onclick="redirect(this)">Gestione Magazzino</button>
           	</div>

       </div>
       <div class="right-column">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">ScanIt</h1>
           <div class="article mt-4 border-black border">
               <h2>Puoi scegliere tra i seguenti servizi:</h2>
               <div class="grid grid-cols-2 gap-4">
               		<div class="ml-4 mt-4">
              	    	<button class="px-7 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="vs" onclick="redirect(this)">Visualizza Scorte</button>
	           		</div>
	           		<div class="ml-4 mt-4 right-align">
	                	<button class="px-9 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="os" onclick="redirect(this)">Ordina Scorte</button>
	           		</div>
	           		<div class="ml-4 mt-4">
	               		<button class="px-5 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="ai" onclick="redirect(this)">Aggiorna Inventario</button>
	           		</div>
	           		<div class="ml-4 mt-4 right-align">
	               		<button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="vr" onclick="redirect(this)">Visualizza Resoconti</button>
	           		</div>    
               </div>   
           </div>
       </div>
   </div>

</body>
</html>