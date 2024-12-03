document.addEventListener("DOMContentLoaded", function () {
    var CORRECT_USER = "phamngoc04";
    var CORRECT_PASS = "123";

    var inputUsername = document.getElementById("username");
    var inputPassword = document.getElementById("password");
    var formLogin = document.getElementById("loginForm");
    var divErrors = document.getElementById("errors");
    var errorsUser = document.querySelector(".error-username");
    var errorsPassword = document.querySelector(".error-password");

    inputUsername.value = "";
    inputPassword.value = "";

    formLogin.addEventListener("submit", function (e) {
        e.preventDefault();
        errorsUser.innerText = "";
        errorsPassword.innerText = "";
        var username = inputUsername.value.trim();
        var password = inputPassword.value.trim();

        if (username === CORRECT_USER && password === CORRECT_PASS) {
            window.location.href = "https://getbootstrap.com/";
        } else {
            if (username !== CORRECT_USER) {
                errorsUser.innerText = "Sai tài khoản";
            } else {
                errorsUser.innerText = "";
            }
            if (password !== CORRECT_PASS) {
                errorsPassword.innerText = "Sai mật khẩu";
            } else {
                errorsPassword.innerText = "";
            }

            divErrors.innerHTML = `
                <div class="alert alert-danger">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <strong>Error:</strong> Invalid username or password!
                </div>
            `;
        }

        inputPassword.value = "";
    });
});

document
        .querySelector(".forgot-password")
        .addEventListener("click", function (e) {
            e.preventDefault();
            window.location.href = "forgot.html";
});