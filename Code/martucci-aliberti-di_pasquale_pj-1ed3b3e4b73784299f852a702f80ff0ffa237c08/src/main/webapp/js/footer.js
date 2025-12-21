let modal;
document.addEventListener('DOMContentLoaded', () => {
    modal = document.getElementById("modalIscrizione");
    window.modal = modal; // ora è accessibile anche da console
    const btn = document.getElementById("btnIscriviti");
    const span = modal.querySelector(".close");
    const emailInput = document.getElementById("newsletterEmail");


    btn.onclick = () => {
        const email = emailInput.value.trim();
        const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        if (email === "" || !emailRegex.test(email)) {
            alert("Inserisci una email valida!");
            return;
        }

        modal.style.display = "block";
        emailInput.value = "";
    };

    span.onclick = () => {
        modal.style.display = "none";
    };

    window.onclick = (event) => {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
});

