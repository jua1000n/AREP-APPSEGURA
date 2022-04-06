async function inputValues() {
    name = document.getElementsByName("user")[0].value;
    pass = document.getElementsByName("pass")[0].value;

    dateUser = {name, pass};
    console.log(dateUser);


    const date = await fetch(`localhost:4567/login`, {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(dateUser)
    });
}