function show(id) {
    var x = document.getElementById(id);
    if (x.style.opacity == "0") {
        x.style.opacity = "1";
    } else {
        x.style.opacity = "0";
    }
  }

function downSearch(id){
    show(id);
    var x = document.getElementById(id);
    if(x.style.top == "43px"){
        x.style.top = "-100px";
    }
    else {
        x.style.top = "43px";
    }
}
