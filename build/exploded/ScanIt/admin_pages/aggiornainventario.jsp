<%@ page import="ingSw_beans.SessionMap"%>
<%@ page import="ingSw_beans.Ordine"%>
<%@ page import="ingSw_beans.Prodotto"%>
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
  <title>Aggiorna Inventario</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');
  </style>
<link rel="stylesheet" href="../styles/admin.css">
<script src="../scripts/aggiornainventario.js"></script>
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

int indexOrdine = 0;
%>

<body class="bg-white dark:bg-gray-900">
   <div class="container">
       <div class="left-column mt-4">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">Servizi</h1>
           
           <div class="mt-4">
               <button class="px-7 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="vs" onclick="redirect(this)">Visualizza Scorte</button>
           </div>
           <div class="mt-4">
               <button class="px-9 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="os" onclick="redirect(this)">Ordina Scorte</button>
           </div>
           <div class="mt-4">
               <button class="px-5 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="ai" onclick="redirect(this)">Aggiorna Inventario</button>
           </div>
           <div class="mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="vr" onclick="redirect(this)">Visualizza Resoconti</button>
           </div>
           
           <div class="mt-4">
               <button class="buttonspace px-5 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="gp" onclick="redirect(this)">Gestione Personale</button>
           </div>
           
       </div>
       <div class="right-column">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400" class="text-3xl font-bold text-blue-600 dark:text-blue-400">ScanIt</h1>
           <div class="article mt-4 border-black border">
               <h2>Aggiorna inventario</h2>
               <table class="mt-4 w-full text-left border-collapse" id="tabellaOrdini">
			            <thead>
			                <tr>
			                    <th class="border-b-2 p-2"><strong>ID Ordine</strong></th>
			                    <th class="border-b-2 p-2"><strong>Data</strong></th>
			                </tr>
			            </thead>
			            <tbody>
			           			
			                <% for (Ordine o : db.listaOrdini()) { %>
				                
			                    <tr>
			                        <td class="border-b p-2" id="idOrdine<%=indexOrdine%>"><%= o.getId() %></td>
			                        <td class="border-b p-2" id="data<%=indexOrdine%>"><%= o.getData() %></td>
			                        <td class="border-b p-2"><button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="visualizza" onclick="visualizza(<%=indexOrdine%>)">Visualizza Ordine</button></td>
			                        <td class="border-b p-2"><button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="conferma" onclick="confermaArrivo(<%=indexOrdine%>)">Conferma arrivo</button></td>			                        
			                    </tr>
			                    <%for (Prodotto p : o.getProdotti().keySet()){ %>
			                    <tr class="hidden-info" style="display: none;" id="idProdotto<%= indexOrdine %>">
			                    	<td colspan="3" class="border-b p-2"><em><strong>ID Prodotto</strong></em></td>
						            <td colspan="3" class="border-b p-2"><%= p.getId() %></td>
						        </tr>
						        <tr class="hidden-info" style="display: none;" id="nomeProdotto<%= indexOrdine %>">
						        	<td colspan="3" class="border-b p-2"><em>Nome</em></td>
						            <td colspan="3" class="border-b p-2"><%= p.getNome() %></td>
						        </tr>
						        <tr class="hidden-info" style="display: none;" id="descrizioneProdotto<%= indexOrdine %>">
						        	<td colspan="3" class="border-b p-2"><em>Descrizione</em></td>
						            <td colspan="3" class="border-b p-2"><%= p.getDescrizione() %></td>
						        </tr>
						        <tr class="hidden-info" style="display: none;" id="idFornitore<%= indexOrdine %>">
						        	<td colspan="3" class="border-b p-2"><em>ID Fornitore</em></td>
						            <td colspan="3" class="border-b p-2"><%= p.getIdFornitore() %></td>
						        </tr>
						        <tr class="hidden-info" style="display: none;" id="quantitaProdotto<%= indexOrdine %>">
						        	<td colspan="3" class="border-b p-2"><em>Quantità ordinata</em></td>
						            <td colspan="3" class="border-b p-2"><%= db.getQuantitaOrdinataFromOrdine(o.getId(), p.getId()) %></td>
						        </tr>
						        
						        <%
			                    } 
			                    %>

			                    <% 
			                   indexOrdine++; 
			                    } 
			                    %>
			            </tbody>
			        </table>
           </div>
       </div>
   </div>
</body>
</html>