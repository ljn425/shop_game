"use strict";

/**
 * 메뉴 하이라이트(활성화)
 */
const currentUri = window.location.pathname;
const sidebarNav = document.getElementById("sidebar-nav");
const navLinks = sidebarNav.querySelectorAll(".nav-link");
const splitUri = currentUri.split("/");
const sharp = "#";

//추가 메뉴 없을 경우
navLinks.forEach(navLink => {
    if (navLink.getAttribute("href") != sharp) {
        if (navLink.getAttribute("href") === currentUri) {
            navLink.classList.remove("collapsed");
        }
    }
});

//추가 메뉴 있을 경우
if (splitUri[2] === "game") {
    toggleSidebar(splitUri[2]);
}

//추가 메뉴 하이라이트 적용 함수
function toggleSidebar(splitUri) {
    const nav = document.getElementById(splitUri+"-nav")
    const navChild = nav.querySelectorAll("li a");
    const previousElementSibling = nav.previousElementSibling;

    previousElementSibling.classList.remove("collapsed");
    nav.classList.add("show");

    navChild.forEach(navCh => {
        if (navCh.getAttribute("href") === currentUri) {
            navCh.classList.add("active");
        }
    });
}





