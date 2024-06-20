<%@ page import="ingSw_beans.SessionMap"%>
<%@ page import="ingSw_beans.ScanItDB"%>
<%@ page import="ingSw_beans.Fornitore"%>

<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="Pragma" content="no-cache"/>
  <meta http-equiv="Expires" content="-1"/>
  <title>Ordina Scorte</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');
  </style>
<link rel="stylesheet" href="../styles/admin.css">
<script src="../scripts/ordinascorte.js"></script>

</head>

<%
/*
	SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
	
	HttpSession s = request.getSession(false); // Recupero session	
	
	if(sessionMap == null){
		response.sendRedirect("../login.html"); 
	}else if (!sessionMap.getASessions().containsKey(s) || s == null){
		response.sendRedirect("../login.html");
	}	
*/

ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
if(db == null)
{
	db = new ScanItDB();
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
           <div class="flex flex-wrap">
			    <div class="w-full md:w-2/3 p-4">
			        <h2 class="text-xl font-bold text-blue-600 dark:text-blue-400">Ordina Scorte</h2>
			        <table class="mt-4 w-full text-left border-collapse" id="tabellaFornitori">
			            <thead>
			                <tr>
			                    <th class="border-b-2 p-2">Nome</th>
			                    <th class="border-b-2 p-2">ID</th>
			                    <th class="border-b-2 p-2">ID Fornitore</th>
			                </tr>
			                <tr>
			                    <th><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="nomeInput" id="nomeInput" size="20"></th>
			                    <th><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="idInput" id="idInput" size="20"></th>
			                    <th><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="idFornitoreInput" id="idFornitoreInput" size="20"></th>
			                    <th><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="submit" name="submit" id="submit" size="20"></th>
			                </tr>
			            </thead>
			            <tbody>
			                <% for (Fornitore f : db.listaFornitori()) { %>
			                    <tr>
			                        <td class="border-b p-2" id="idFornitore<%=index%>"><%= f.getIdFornitore() %></td>
			                        <td class="border-b p-2" id="id<%=index%>"><%= f.getId() %></td>
			                        <td class="border-b p-2" id="costo<%=index%>"><%= f.getCosto() %></td>
			                        <td class="border-b p-2"><button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="add" onclick="add(<%=index%>)">Aggiungi</button></td>
			                    </tr>
			                    <% index++; } %>
			            </tbody>
			        </table>
			    </div>
			
			    <div class="w-full md:w-1/3 p-4 bg-white rounded-md">
			        <h2 class="text-xl font-bold text-blue-600 dark:text-blue-400">Carrello</h2>
			        <table class="mt-4 w-full text-left border-collapse" id="tabellaCarrello">
					    <thead>
					        <tr>
					            <th class="border-b-2 p-2">Nome</th>
					            <th class="border-b-2 p-2">ID</th>
					            <th class="border-b-2 p-2">Costo</th>
					            <th class="border-b-2 p-2">Quantità</th>
					        </tr>
					    </thead>
					    <tbody id="corpoTabellaCarrello">
					        <!-- Qui andranno le righe dinamiche del carrello -->
					    </tbody>
					</table>
					<div class="flex justify-end mt-4">
					    <div class="p-4 bg-blue-600 text-white rounded-md">
						        <span class="font-bold">Costo Totale:</span> <span id="costoTotale">0</span> Euro
						        <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="send" onclick="invia()">Invia Ordine</button>
						    </div>
						</div>
					
			    	</div>
			</div>
       </div>
   </div>
</body>
</html>