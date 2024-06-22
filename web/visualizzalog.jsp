<%@ page import="ingSw_beans.SessionMap"%>
<%@ page import="ingSw_beans.Log"%>
<%@ page import="ingSw_beans.LogController"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.List"%>

<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="-1"/>
<title>Admin</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<style>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');
  body {
    font-family: 'Poppins', sans-serif;
  }
  .hidden-info {
    display: none;
  }
</style>
<script src="scripts/utils.js"></script>
<script src="scripts/visualizzalog.js"></script>
</head>

<%
/*
  SessionMap sessionMap = (SessionMap) this.getServletContext().getAttribute("sessionMap");

  HttpSession s = request.getSession(false); // Recupero session

  if(sessionMap == null){
    response.sendRedirect("login.html"); 
  }else if (!sessionMap.getASessions().containsKey(s) || s == null){
    response.sendRedirect("login.html");
  } 
*/ 
int index = 0;

LocalDate oggi = LocalDate.now();

// Data una settimana fa
LocalDate unaSettimanaFa = oggi.minusWeeks(1);

// Formattatore per il formato "giorno mese anno"
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>

<body class="bg-gray-900 text-white">
  <div class="container mx-auto py-8">
    <div class="left-column"></div>
    <div class="right-column">
      <h1 class="text-3xl font-bold text-blue-600 mb-8">ScanIt</h1>
      <div class="flex flex-wrap">
        <div class="w-full md:w-2/3 p-4">
          <div class="article mt-4">
            <h2 class="text-xl font-bold text-blue-600 mb-4">Visualizza Log</h2>
            <table class="w-full text-left border-collapse bg-white text-black rounded-lg shadow-lg" id="tabellaLogs">
              <thead>
                <tr>
                  <th class="border-b-2 p-2">Data</th>
                  <th class="border-b-2 p-2">Azioni</th>
                </tr>
              </thead>
              <tbody>
                <%for(LocalDate data = oggi; !data.isBefore(unaSettimanaFa); data = data.minusDays(1)) {
                  String dataFormattata = data.format(formatter);
                  List<Log> logs = LogController.getInstance().getLogsByDate(dataFormattata);

                  if(!logs.isEmpty()){
                %>
                <tr>
                  <td class="border-b p-2" id="data<%=index%>"><strong><%= dataFormattata %></strong></td>
                  <td class="border-b p-2">
                    <button class="px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500" id="visualizza" onclick="visualizza(<%=index%>)">Visualizza</button>
                  </td>
                </tr>
                <%        
                  }
                  for(Log l : logs){
                %>
                <tr class="hidden-info" id="username<%=index %>">
                  <td colspan="3" class="border-b p-2"><em>Username:</em> <%= l.getUsername() %></td>
                  <td colspan="3" class="border-b p-2"><em>Operazione:</em> <%= l.getOperazione() %></td>
                  <td colspan="3" class="border-b p-2"><em>Esito:</em> <%= l.getEsito() %></td>
                  <td colspan="3" class="border-b p-2"><em>Timestamp:</em> <%= l.getTimestamp() %></td>
                </tr>
                
                <%} %>
                <% index++; } %>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
