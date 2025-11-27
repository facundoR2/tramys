//-----------------
//--IMPORTS----//
//-----------------

import { abrirModalTramite,cerrarModal} from "/JS/modal.js";

const container = document.getElementById("tramitesContainer");
const noSelectionNote = document.getElementById("noSelectionNote");

// --- Ejemplos ficticios ---
export function getTramitesForLocation(lat, lng) {
    // Datos mockeados. Puedes reemplazar con fetch a tu backend.

    return [
        {
            titulo: "Solicitud de DNI",
            descripcion: "Requisitos para renovar u obtener tu documento.",
            distancia: (Math.random() * 2).toFixed(1) + " km"
        },
        {
            titulo: "Turno pasaporte",
            descripcion: "Oficinas cercanas para tramitar pasaporte.",
            distancia: (Math.random() * 5).toFixed(1) + " km"
        },
        {
            titulo: "Certificado de domicilio",
            descripcion: "Trámite disponible únicamente en comisarías.",
            distancia: (Math.random() * 1.5).toFixed(1) + " km"
        }
    ];
}

// --- Render de cada trámite ---
export function renderTramites(tramites) {
    if(!container) return console.error("Contenedor de trámites no encontrado.");
    if(noSelectionNote) noSelectionNote.style.display = "none";
    

    container.innerHTML = "";

    tramites.forEach(t => {
        const card = document.createElement("div");
        card.className = "tramite-card";
        card.style.padding = "10px";
        card.style.marginBottom = "1px solid #eef2f7";
        card.innerHTML = `
            <div class="tramite-title">${t.titulo}</div>
            <div class="tramite-desc">${t.descripcion}</div>
            <div class="tramite-distancia">A ${t.distancia}</div>
            <div class="tramite-actions">
                <button class="btn primary iniciar-btn">Iniciar tramite</button>
                <button class="btn secondary detalles-btn">Ver detalles</button>
            </div>
        `;
        //---- accion de iniciar tramite---.
        card.querySelector(".iniciar-btn").addEventListener("click", () => {
            alert(`Iniciando trámite: ${t.titulo}`);
            abrirModalTramite(t);
        });
        //--- accion de ver detalles---.
        card.querySelector(".detalles-btn").addEventListener("click", () => {
            alert(`Detalles del trámite: ${t.titulo}\n\n${t.descripcion}`);
            window.location.href = `/tramite.html?titulo=${encodeURIComponent(t.titulo)}`;
        });
        container.appendChild(card);
    });
}

export function clearTramites() {
    if (!container) return;

    container.innerHTML = "";
    if (noSelectionNote) noSelectionNote.style.display = "block";
}