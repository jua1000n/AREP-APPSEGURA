async function inputValues() {
    name = document.getElementsByName("user")[0].value;
    password = document.getElementsByName("pass")[0].value;

    dateUser = {name, password};
    console.log(dateUser);


    const date = await fetch(`http://localhost:4567/login`, {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(dateUser)
    });
}