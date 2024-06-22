function redirect(e){
	   if(e.id === "ai"){
		   window.location.href = 'aggiornainventario.jsp';
	   }else if(e.id === "gp"){
		   window.location.href = 'gestionepersonale.jsp';
	   }else if(e.id === "os"){
		   window.location.href = 'ordinascorte.jsp';
	   }else if(e.id === "vr"){
		   window.location.href = 'visualizzaresoconti.jsp';
	   }else if(e.id === "vs"){
		   window.location.href = 'visualizzascorte.jsp';
	   }else if (e.id === "gm"){
		   window.location.href = 'gestionemagazzino.jsp';
	   }
   }