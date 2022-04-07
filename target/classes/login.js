async function inputValues() {
    name = document.getElementsByName("user")[0].value;
    password = document.getElementsByName("pass")[0].value;

    dateUser = {name, password};
    console.log(dateUser);


    const date = await fetch(`https://ec2-107-20-74-184.compute-1.amazonaws.com:4567/login`, {
        method: "POST",
        headers: {
            "Content-Type" : "application/json"
        },
        body: JSON.stringify(dateUser)
    });
    const res = await date.json();

    console.log(res)
    var x = document.getElementById("resultt");
    x.querySelector(".example").innerHTML = (res.server);
    if(res.status == 202) {
        window.location.replace("https://ec2-18-205-25-110.compute-1.amazonaws.com:5000/hello");
    }
}