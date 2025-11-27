export function abrirModalTramite(t) {
    document.getElementById("modalTitle").textContent = t.titulo;
    document.getElementById("modalDescription").textContent = t.descripcion;
    document.getElementById("tramiteModal").classList.remove("hidden");
}

export function cerrarModal() {
    document.getElementById("tramiteModal").classList.add("hidden");
}
document.getElementById("cerrarModal")?.addEventListener("click",cerrarModal);