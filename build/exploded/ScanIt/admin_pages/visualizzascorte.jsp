<%@ page import="ingSw_beans.SessionMap"%>
<%@ page import="ingSw_beans.Prodotto"%>
<%@ page import="ingSw_beans.ScanItDB"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="Expires" content="-1"/>
  <title>Visualizza Scorte</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');
    
    .low-stock {
        color: red;
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


ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
if(db == null)
{
	db = ScanItDB.getInstance();
	this.getServletContext().setAttribute("db", db);
}


%>


<body class="bg-white dark:bg-gray-900">
   <div class="container">
       <div class="left-column mt-4">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">Servizi</h1>
           
           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="vs" onclick="redirect(this)">Visualizza Scorte</button>
           </div>
           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="os" onclick="redirect(this)">Ordina Scorte</button>
           </div>
           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="ai" onclick="redirect(this)">Aggiorna Inventario</button>
           </div>
           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="vr" onclick="redirect(this)">Visualizza Resoconti</button>
           </div>
           
           <div class="genre mt-4">
               <button class="buttonspace px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="gp" onclick="redirect(this)">Gestione Personale</button>
           </div>

           
       </div>
       <div class="right-column">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400" class="text-3xl font-bold text-blue-600 dark:text-blue-400">ScanIt</h1>
           <div class="article mt-4">
               <h2>Visualizza Scorte</h2>
               
                   <table class="mt-4 w-full text-left border-collapse" id="tabellaProdotti">
                   <thead>
                       <tr>
                           <th class="border-b-2 p-2">ID</th>
                           <th class="border-b-2 p-2">Nome</th>
                           <th class="border-b-2 p-2">Descrizione</th>
                           <th class="border-b-2 p-2">ID Fornitore</th>
                           <th class="border-b-2 p-2">Prezzo</th>
                           <th class="border-b-2 p-2">Disponibilità</th>
                       </tr>
                   </thead>
                   <tbody>
				    <% 
				    for (Prodotto p : db.listaProdotti()) { 
				    	String lowStockClass = p.getQuantita() < 10 ? "low-stock" : "";
				    	%>
				        <tr>
				            <td class="border-b p-2"><%= p.getId() %></td>
				            <td class="border-b p-2"><%= p.getNome() %></td>
				            <td class="border-b p-2"><%= p.getDescrizione() %></td>
				            <td class="border-b p-2"><%= p.getIdFornitore() %></td>
				            <td class="border-b p-2"><%= p.getPrezzo() %></td>
				           	<td class="border-b p-2 <%= lowStockClass %>"><%= p.getQuantita() %></td>
				            
				        </tr>
				        
				       
				    <% 
				    } 
				    %>
				    
				</tbody>
               </table>
               
           </div>
       </div>
   </div>
</body>
</html>