function checkPW(){
	var np = document.getElementById("nuovapassword");
	var cnp = document.getElementById("confermanuovapassword");
	var sub = document.getElementById("sub");
	const regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!?,\$\%\&�@]).{8,}$/;
    const equal = document.getElementById("equal");
    const reg = document.getElementById("regex");


	
	if(np.value === cnp.value && regex.test(np.value)){
		sub.disabled = false;
		equal.style.display = 'none';
		reg.style.display = 'none';
		sub.style.background = "green";

	}
	
	if(np.value !== cnp.value){
		equal.style.display = 'block';
		sub.style.background = "grey";

	}else if(np.value === cnp.value){
		equal.style.display = 'none';
	}
	if(!regex.test(np.value)){
		reg.style.display = 'block';
		sub.style.background = "grey";

	}else if(regex.test(np.value)){
		reg.style.display = 'none';
	}
	
	
}

 function check(current, nextFieldID) {
     current.value = current.value.replace(/[^0-9]/g, '');
     if (current.value.length === 1) {
         if (nextFieldID) {
             document.getElementById(nextFieldID).focus();
         } else {
        	 
        	 var obj = {};
        		obj.otp1 = document.getElementById("otp1").value;
        		obj.otp2 = document.getElementById("otp2").value;
        		obj.otp3 = document.getElementById("otp3").value;
        		obj.otp4 = document.getElementById("otp4").value;
        		obj.otp5 = document.getElementById("otp5").value;
        	 
        	 fetch('/ScanIt/ChangePWServlet', {
        	        method: 'POST',
        	        headers: {
        	            'Content-Type': 'application/json',
        	        },
        	        body: JSON.stringify(obj)
        	    })
        	    .then(response => {
        	        if (!response.ok) {
    	                throw new Error("Codice errato!: " + response.status);
        	        }
        	        return response.text();
        	    })
        	    .then(data => {
        	        alert("Password cambiata correttamente!");
        	        console.log(data);
        	        window.location.replace("login.html");
        	    })
        	    .catch(error => {
        	        alert(error.message); 
        	        console.error('Errore:', error);
        	        document.getElementById('error').style.display = 'block';
        	        location.reload();
        	    });
         }
     }
 }

function getQueryParams() {
    const params = {};
    const queryString = window.location.search.substring(1);
    const regex = /([^&=]+)=([^&]*)/g;
    let m;
    while (m = regex.exec(queryString)) {
        params[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
    }
    return params;
}

window.onload = function() {
    const params = getQueryParams();
    const username = document.getElementById('username');
	const np = document.getElementById('nuovapassword');
	const cnp = document.getElementById('confermanuovapassword');

    if (params['OTP'] === 'true') {
    	const otp1 = document.getElementById('otp1');
        const otp2 = document.getElementById('otp2');
        const otp3 = document.getElementById('otp3');
        const otp4 = document.getElementById('otp4');
        const otp5 = document.getElementById('otp5');
    	const otptext = document.getElementById('otptext');
    	const equal = document.getElementById("equal");
        const reg = document.getElementById("regex");
    	
        otp1.style.visibility = 'visible';
        otp2.style.visibility = 'visible';
        otp3.style.visibility = 'visible';
        otp4.style.visibility = 'visible';
        otp5.style.visibility = 'visible';
        otptext.style.visibility = 'visible';
		equal.style.display = 'none';
		reg.style.display = 'none';
		
        username.disabled = true;
        np.disabled = true;
        cnp.disabled = true;
    }else{
    	username.disabled = false;
    	np.disabled = false;
    	cnp.disabled = false;
    }
    
};
