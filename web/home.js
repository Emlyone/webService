url = 'social.html'; 

function connexion () {
    var pseudo = document.getElementById("pseudo");
    if ( pseudo.value == null) {
        alert("Veuillez entrer un pseudo!");
    } 
    else {
        window.location.replace(url+"?pseudo="+pseudo.value);
    }
}