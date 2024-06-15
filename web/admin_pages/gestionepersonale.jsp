<%@ page import="ingSw_beans.SessionMap"%>
<%@ page import="ingSw_beans.UserDb"%>
<%@ page import="ingSw_beans.Dipendente"%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="-1"/>
<title>Gestione Personale</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
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
UserDb userDb = (UserDb) this.getServletContext().getAttribute("userDb");
if(userDb == null) {
    userDb = new UserDb();
    this.getServletContext().setAttribute("userDb", userDb);
}
SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");
if(sessionMap == null) {
    sessionMap = new SessionMap();
    this.getServletContext().setAttribute("sessionMap", sessionMap);
}

// Contatore per gli indici dei dipendenti
int index = 0;
%>
<body class="bg-white dark:bg-gray-900">
   <div class="container">
       <div class="left-column mt-4">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">Gestione Personale</h1>

           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="gp" onclick="redirect(this)">Gestione Personale</button>
           </div>
           <div class="genre mt-4">
               <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 dark:bg-blue-400 dark:hover:bg-blue-500 dark:focus:ring-offset-gray-800" id="gm" onclick="redirect(this)">Gestione Magazzino</button>
           </div>
       </div>
       <div class="right-column">
           <h1 class="text-3xl font-bold text-blue-600 dark:text-blue-400">ScanIt</h1>
           <div class="article mt-4">
               <h2 class="text-center text-2xl">Lista dei Dipendenti</h2>
               <table class="mt-4 w-full text-left border-collapse">
                   <thead>
                       <tr>
                           <th class="border-b-2 p-2">ID</th>
                           <th class="border-b-2 p-2">Nome</th>
                           <th class="border-b-2 p-2">Cognome</th>
                       </tr>
                   </thead>
                   <tbody>
				    <% for (Dipendente d : userDb.getDipendenti()) { %>
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
				                <button class="px-4 py-2 text-sm font-medium text-white bg-red-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 dark:bg-red-400 dark:hover:bg-red-500 dark:focus:ring-offset-gray-800" onclick="licenzia(this)">Licenzia</button>
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
				    <% 
				        // Incrementa l'indice per il prossimo dipendente
				        index++;
				    } %>
				</tbody>
               </table>
           </div>
       </div>
   </div>

   <script src="../scripts/admin.js"></script>
</body>
</html>
