<%@ page import="ingSw_beans.SessionMap"%>
<%@ page import="ingSw_beans.Resoconto"%>
<%@ page import="ingSw_beans.Scansione"%>
<%@ page import="ingSw_beans.ScanItDB"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

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
<script src="../scripts/visualizzaresoconti.js"></script>
<script src="../scripts/utils.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>

</head>

<%

SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");

HttpSession sess = request.getSession(false); // Recupero session	

if(sessionMap == null){
	response.sendRedirect("../login.html"); 
}else if (!sessionMap.getASessions().containsKey(sess) || sess == null){
	response.sendRedirect("../login.html");
}	



ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
if(db == null)
{
	db = ScanItDB.getInstance();
	this.getServletContext().setAttribute("db", db);
}

int index = 0;
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
               <h2>Visualizza resoconti</h2>
               <table class="mt-4 w-full text-left border-collapse" id="tabellaOrdini">
			            <thead>
			                <tr>
			                    <th class="border-b-2 p-2"><strong>ID</strong></th>
			                    <th class="border-b-2 p-2"><strong>Data</strong></th>
			                </tr>
			            </thead>
			            <tbody>
			           			
			                <% for (Resoconto r : db.listaResoconti()) { %>
				                
			                    <tr>
			                        <td class="border-b p-2" id="id<%=index%>"><%= r.getId() %></td>
			                        <td class="border-b p-2" id="data<%=index%>"><%= r.getData() %></td>
			                        <td class="border-b p-2"><button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="visualizza" onclick="visualizza(<%=index%>)">Visualizza</button></td>
			                        <td class="border-b p-2"><button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="conferma" onclick="scarica(<%=index%>)">Scarica</button></td>			                        
			                    </tr>
			                    <tr class="hidden-info" style="display: none;" id="guadagno<%= index %>">
			                    	<td colspan="3" class="border-b p-2"><em><strong>Guadagno della giornata in Euro: <%=r.calcolaGuadagnoTotale() %></strong></em></td>
			                    </tr>
			                    <%for (Scansione s : db.getScansioniPerGiornoDaResoconto(r.getData())){ %>
			                    <tr class="hidden-info" style="display: none;" id="username<%= index %>">
			                    	<td colspan="3" class="border-b p-2"><em><strong>Username</strong></em></td>
						            <td colspan="3" class="border-b p-2"><%= s.getUsername() %></td>
						        </tr>
						        <tr class="hidden-info" style="display: none;" id="idProdotto<%= index %>">
						        	<td colspan="3" class="border-b p-2"><em>ID Prodotto</em></td>
						            <td colspan="3" class="border-b p-2"><%= s.getIdProdotto() %></td>
						        </tr>
						        <tr class="hidden-info" style="display: none;" id="quantita<%= index %>">
						        	<td colspan="3" class="border-b p-2"><em>Quantità</em></td>
						            <td colspan="3" class="border-b p-2"><%= s.getQuantita() %></td>
						        </tr>
						        <tr class="hidden-info" style="display: none;" id="timestamp<%= index %>">
						        	<td colspan="3" class="border-b p-2"><em>Timestamp</em></td>
						            <td colspan="3" class="border-b p-2"><%= s.getTimestamp() %></td>
						        </tr>
						      
						        
							        <%
				                    } 
				                    %>

			                    <% 
			                   index++; 
			                    } 
			                    %>
			            </tbody>
			        </table>
           </div>
       </div>
   </div>
</body>
</html>