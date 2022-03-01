window.onscroll = function() {scrollFunction("top-btn")};

function scrollFunction() {
    topButton = document.getElementById("top-btn");
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    topButton.style.display = "block";
  } else {
    topButton.style.display = "none";
  }
}

function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

function botFunction() {
  window.scrollTo(0,document.body.scrollHeight);
}