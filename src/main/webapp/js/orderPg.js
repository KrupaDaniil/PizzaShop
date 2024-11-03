document.addEventListener("DOMContentLoaded", (e) => {
   let phone = document.getElementById("userPhone");
   phone.addEventListener("keypress", (e) => {
       if (!/\d/.test(e.key)) {
           e.preventDefault();
       }
   })
});