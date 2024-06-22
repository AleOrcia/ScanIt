<%@ page import="ingSw_beans.SessionMap"%>
<%@ page import="ingSw_beans.Dipendente"%>
<%@ page import="ingSw_beans.ScanItDB"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="-1"/>
<title>Gestione Personale</title>
<script src="https://cdn.tailwindcss.com"></script>
<!--  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">-->
<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');

    .triangle-button {
        width: 0;
        height: 0;
        border-left: 10px solid transparent;
        border-right: 10px solid transparent;
        border-top: 20px solid #000; /* Change color as needed */
        cursor: pointer;
    }
    
    .triangle-up {
        border-top: none;
        border-bottom: 20px solid #000; /* Cambia il colore e la direzione del triangolo */
    }
    
    .hidden-info {
        display: none;
    }
</style>
<link rel="stylesheet" href="../styles/admin.css">
</head>

<%
ScanItDB db = (ScanItDB) this.getServletContext().getAttribute("db");
if(db == null)
{
	db = new ScanItDB();
	this.getServletContext().setAttribute("db", db);
}

SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");

HttpSession s = request.getSession(false); // Recupero session	

if(sessionMap == null){
	response.sendRedirect("../login.html"); 
}else if (!sessionMap.getASessions().containsKey(s) || s == null){
	response.sendRedirect("../login.html");
}	

// Contatore per gli indici dei dipendenti
int index = 0;
%>
<body class="bg-white dark:bg-gray-900">
   <div class="container">
       <div class="left-column mt-4">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">Servizi</h1>

           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-500 rounded-md shadow-sm hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-400 dark:bg-blue-300 dark:hover:bg-blue-400 dark:focus:ring-offset-gray-700" id="gp" onclick="redirect(this)">Gestione Personale</button>
           </div>
           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="gm" onclick="redirect(this)">Gestione Magazzino</button>
           </div>
       </div>
       <div class="right-column">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">ScanIt</h1>
           <div class="article mt-4">
               <h2 class="text-center text-2xl" id="h2list">Lista dei Dipendenti</h2>
               <table class="mt-4 w-full text-left border-collapse" id="tabellaDipendenti">
                   <thead>
                       <tr>
                           <th class="border-b-2 p-2">ID</th>
                           <th class="border-b-2 p-2">Nome</th>
                           <th class="border-b-2 p-2">Cognome</th>
                       </tr>
                   </thead>
                   <tbody>
				    <% 
				    for (Dipendente d : db.getDipendenti()) { %>
				        <tr>
				            <td class="border-b p-2">
				                <div class="flex items-center">
				                    <div class="triangle-button mr-2" onclick="showDipendente(this, <%= index %>)"></div>
				                    <%= d.getUsername() %>
				                </div>
				            </td>
				            <td class="border-b p-2"><%= d.getNome() %></td>
				            <td class="border-b p-2"><%= d.getCognome() %></td>
				            <td class="border-b p-2">
				                <button class="px-4 py-2 text-sm font-medium text-white bg-red-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 dark:bg-red-400 dark:hover:bg-red-500 dark:focus:ring-offset-gray-800" onclick="licenzia('<%= d.getUsername()%>', <%=index %>)">Licenzia</button>
				            </td>
				        </tr>
				        
				        <!-- Righe aggiuntive per le informazioni nascoste -->
				        <tr class="hidden-info" style="display: none;" id="indirizzo<%=index %>">
				            <td colspan="3" class="border-b p-2"><strong>Indirizzo:</strong> <%= d.getIndirizzo() %></td>
				        </tr>
				        <tr class="hidden-info" style="display: none;" id="cellulare<%=index %>">
				            <td colspan="3" class="border-b p-2"><strong>Cellulare:</strong> <%= d.getNumeroTelefono() %></td>
				        </tr>
				        <tr class="hidden-info" style="display: none;" id="data<%=index %>">
				            <td colspan="3" class="border-b p-2"><strong>Data di nascita:</strong> <%= d.getDataNascita() %></td>
				        </tr>
				        <tr class="hidden-info" style="display: none;" id="codice<%=index %>">
				            <td colspan="3" class="border-b p-2"><strong>Codice identificativo:</strong> <%= d.getIdDocumento() %></td>
				        </tr>
				        <tr>
				        	<td><div id="confermaLicenziamento<%=index%>"></div></td>
				        </tr>
				    <% 
				        // Incrementa l'indice per il prossimo dipendente
				        index++;
				    } %>
				    
				</tbody>
               </table>
               		<h2 class="text-center text-2xl" id="h2new" hidden="hidden">Dati del nuovo dipendente</h2>
               		
               		<form class="mt-4 w-full text-left border-collapse" id="tabellaAssunzione"  hidden="hidden" action="/ScanIt/RegistraServlet">
               			<table>
               				<tr>
               					<td>Nome:</td>
               					<td><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="nome" id="nome" size="40"></td>
               				</tr>
               				<tr>
               				<td>Cognome:</td>
               				<td><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="cognome" id="cognome" size="40"></td>
	               			</tr>
	               			<tr>
	               				<td>Data di nascita (formato gg/mm/aaaa):</td>
	               				<td><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="data" id="data" size="20"></td>
	               			</tr>
	               			<tr>
	               				<td>Numero di telefono:</td>
	               				<td><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="telefono" id="telefono" size="20"></td>
	               			</tr>
	               			<tr>
	               				<td>Indirizzo:</td>
	               				<td><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="indirizzo" id="indirizzo" size="50"></td>
	               			</tr>
	               			<tr>
	               				<td>Documento di identificazione:</td>
	               				<td><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="documento" id="documento" size="20"></td>
	               			</tr>
	               			<tr>
	               				<td>Username:</td>
	               				<td><input class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="text" name="username" id="username" size="5" onkeyup="checkData()"></td>
	               			</tr>
	               			<tr>
               				<td><input disabled="disabled" type="submit" id="submitButton" class='px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800'></td>
               				</tr>
	               		</table>
	               			<div id="error-message" class="hidden text-red-600">
            					Attenzione, alcuni valori non sono corretti!
        					</div>
        					<div id="error" class="hidden text-red-600">
            					Registrazione fallita!
        					</div>
               		</form>
               		
               <button class='px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800' onclick='cambiaSchermata(this)'>Assumi</button>
           </div>
       </div>
   </div>

   <script src="../scripts/admin.js"></script>
   <script src="../scripts/utils.js"></script>
   <script>
   window.onload = function() {
	    const params = getQueryParams();
	    const error = document.getElementById('error');

	    if (params['registrazione'] === 'false') {
	    	error.classList.remove('hidden');
	    }else{
	    	error.classList.add('hidden');
	    }
	    
	};
   </script>
</body>
</html>
