//-----------IMPORTS-----------//
import { crearMapa, onMapClick, attachMapClickHandler } from "/JS/map.js";
import { reverseGeocode } from "/JS/nominatim.js";
import { addControls } from "/JS/controls.js";



//-----------VARIABLES-----------//
const ubicacionInput = document.getElementById("ubicacion");
const pasosContainer = document.getElementById("pasos-container");
 
let contadorPasos = 0;
let Pasos = [];

//-----------FUNCIONES-----------//
// Inicializar el mapa
const map = crearMapa();



//-----------LÓGICA PRINCIPAL-----------//
//funcionalidad para los pasos del tramite.

function agregarPaso() {
    contadorPasos++;

    const indice = Pasos.length;

    Pasos.push({
        nombre: "",
        descripcion:"",
        documentacion: []
    });

    document.getElementById("pasos-container").innerHTML += `
    <div class="paso-item" data-index="${indice}">
        <h4>Paso ${indice + 1}</h4>
        <div class="form-group">
            <label for="titulo-paso-${indice}">Título del Paso:</label>
            <input type="text" id="titulo-paso-${indice}" name="titulo-paso-${indice}" required>
        </div>
        <div class="form-group">
            <label for="descripcion-paso-${indice}">Descripción del Paso:</label>
            <textarea id="descripcion-paso-${indice}" name="descripcion-paso-${indice}" required></textarea>
        </div>
        <button type="button" class="btn btn-danger eliminar-paso-btn" data-index="${indice}">Eliminar Paso</button>
        <hr>
        <div class="documentacion-container" id="documentacion-container-${indice}">
            <h5>Documentación Requerida</h5>
            <button type="button" class="btn btn-secondary agregar-documento-btn" data-index="${indice}">Agregar Documento</button>
            <div class="documentos-lista" id="documentos-lista-${indice}"></div>
        </div>
    </div>
    `;
    // Agregar evento para eliminar paso
    document.querySelector(`.eliminar-paso-btn[data-index="${indice}"]`).addEventListener("click", function() {
        const index = this.getAttribute("data-index");
        eliminarPaso(index);
    });
}

function eliminarPaso(index) {
    Pasos.splice(index, 1);
    contadorPasos--;
    // Re-renderizar la lista de pasos
    pasosContainer.innerHTML = "";
    contadorPasos = 0;
    Pasos.forEach(() => agregarPaso());
}
function agregarDocumento(indexPaso) {
    Pasos[indexPaso].documentos.push({
        nombre: "",
        contenido:""
    });
    // Lógica para agregar el documento en la interfaz
    const iDoc = Pasos[indexPaso].documentos.length - 1;
    document.getElementById(`documentos-lista-${indexPaso}`).innerHTML += `
    <div class="documento-item" data-index-paso="${indexPaso}" data-index-doc="${iDoc}">
        <h6>Documento ${iDoc + 1}</h6>
        <div class="form-group">
            <label for="nombre-documento-${indexPaso}-${iDoc}">Nombre del Documento:</label>
            <input type="text" id="nombre-documento-${indexPaso}-${iDoc}" name="nombre-documento-${indexPaso}-${iDoc}" required>
        </div>
        <div class="form-group
">
            <label for="contenido-documento-${indexPaso}-${iDoc}">Contenido del Documento:</label>
            <textarea id="contenido-documento-${indexPaso}-${iDoc}" name="contenido-documento-${indexPaso}-${iDoc}" required></textarea>
        </div>
        <button type="button" class="btn btn-danger eliminar-documento-btn" data-index-paso="${indexPaso}" data-index-doc="${iDoc}">Eliminar Documento</button>
        <hr>
    </div>
    `;
}
function guardarPasos(){ 
    const pasos = [];
    for (let i = 1; i <= contadorPasos; i++) {
        const titulo = document.getElementById(`titulo-paso-${i}`)?.value;
        const descripcion = document.getElementById(`descripcion-paso-${i}`)?.value;
        if (titulo && descripcion) {
            pasos.push({ titulo, descripcion });
        }
    }
    return pasos;
}
async function PostTramite(tramite) {
    try {
        const response = await fetch('/api/tramites/crear',{
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify(tramite)
        })
        if (!response.ok) {
            throw new Error ('Error al guardar el trámite');
        }
        if (response.ok && response.success === true){ 
            alert ("Trámite guardado con éxito.");
            console.log(response.message);
            window.location.href = "/usuario.html";
        }


    } catch (error) {
        console.error('Error al guardar el trámite:', error);
    }
    
}
function guardarTramite() {
    let usuario = localStorage.getItem("usuario");
    if (usuario) {
        usuario = JSON.parse(usuario);
    } else {
        alert("No se encontró información del usuario. Por favor, inicie sesión.");
        return;
    }
    const nombreTramite = document.getElementById("nombre").value;
    const recinto = ubicacionInput.value;
    const pasos = guardarPasos();
    const tramite = {
        nombre: nombreTramite,
        recinto: recinto,
        pasos: pasos,
        documentacion: [],
        usuarioId: usuario.id
    };
    if (!nombreTramite || !ubicacion || pasos.length === 0) {
        alert("Por favor, complete todos los campos del trámite antes de guardar.");
        return;
    }
    PostTramite(tramite);

    console.log("Trámite guardado:", tramite);

}



//-----------EVENTOS-----------//
document.getElementById("agregar-paso").addEventListener("click", agregarPaso);
document.getElementById("guardar-tramite").addEventListener("click", () => {
    guardarTramite();

});