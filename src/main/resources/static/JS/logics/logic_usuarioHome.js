//-----------------------
//----IMPORTS----//
//-----------------------

//-----------------------
//----FUNCIONES----//




//-----------------------
// datos de ejemplo
//-----------------------

const datosEjemplo = {
            stats: {
                tramites: 5,
                recintos: 3,
                usuarios: 142,
                solicitudes: 12
            },
            tramites: [
                {
                    id: 1,
                    nombre: "Licencia de Conducir",
                    descripcion: "Renovación de licencia de conducir",
                    edificio: "Tránsito Municipal",
                    pasos: 4
                },
                {
                    id: 2,
                    nombre: "Registro Civil",
                    descripcion: "Certificado de nacimiento",
                    edificio: "Registro Civil",
                    pasos: 2
                }
            ],
            recintos: [
                {
                    id: 1,
                    nombre: "Oficina Central",
                    ubicacion: "Avenida Principal 123",
                    horario: "08:00 - 17:00"
                },
                {
                    id: 2,
                    nombre: "Sucursal Norte",
                    ubicacion: "Calle Nueva 456",
                    horario: "09:00 - 16:00"
                }
            ]
        };

        // Cargar estadísticas
        function cargarEstadisticas() {
            document.getElementById('statTramites').textContent = datosEjemplo.stats.tramites;
            document.getElementById('statRecintos').textContent = datosEjemplo.stats.recintos;
            document.getElementById('statUsuarios').textContent = datosEjemplo.stats.usuarios;
            document.getElementById('statSolicitudes').textContent = datosEjemplo.stats.solicitudes;
        }

        // Cargar trámites
        function cargarTramites() {
            const container = document.getElementById('tramitesContainer');
            
            if (datosEjemplo.tramites.length === 0) {
                container.innerHTML = `
                    <div class="empty-state">
                        <div class="empty-state-icon">📋</div>
                        <p>No has creado ningún trámite todavía</p>
                        <a href="/crear-tramite" class="btn" style="text-decoration: none; display: inline-block; padding: 10px 16px;">Crear mi primer trámite</a>
                    </div>
                `;
                return;
            }

            container.innerHTML = datosEjemplo.tramites.map(tramite => `
                <div class="item-card">
                    <div class="item-header">
                        <h3 class="item-title">${tramite.nombre}</h3>
                        <span class="item-badge badge-tramite">Trámite</span>
                    </div>
                    <p class="item-info">${tramite.descripcion}</p>
                    <p class="item-info"><strong>Edificio:</strong> ${tramite.edificio}</p>
                    <p class="item-info"><strong>Pasos:</strong> ${tramite.pasos}</p>
                    <div class="item-actions">
                        <button class="btn secondary" onclick="editarTramite(${tramite.id})">Editar</button>
                        <button class="btn secondary" onclick="verTramite(${tramite.id})">Ver</button>
                    </div>
                </div>
            `).join('');
        }

        // Cargar recintos
        function cargarRecintos() {
            const container = document.getElementById('recintosContainer');
            
            if (datosEjemplo.recintos.length === 0) {
                container.innerHTML = `
                    <div class="empty-state">
                        <div class="empty-state-icon">📍</div>
                        <p>No has creado ningún recinto todavía</p>
                        <a href="/crear-recinto" class="btn" style="text-decoration: none; display: inline-block; padding: 10px 16px;">Crear mi primer recinto</a>
                    </div>
                `;
                return;
            }

            container.innerHTML = datosEjemplo.recintos.map(recinto => `
                <div class="item-card">
                    <div class="item-header">
                        <h3 class="item-title">${recinto.nombre}</h3>
                        <span class="item-badge badge-recinto">Recinto</span>
                    </div>
                    <p class="item-info"><strong>Ubicación:</strong> ${recinto.ubicacion}</p>
                    <p class="item-info"><strong>Horario:</strong> ${recinto.horario}</p>
                    <div class="item-actions">
                        <button class="btn secondary" onclick="editarRecinto(${recinto.id})">Editar</button>
                        <button class="btn secondary" onclick="verRecinto(${recinto.id})">Ver</button>
                    </div>
                </div>
            `).join('');
        }

        // Funciones de acciones
        function editarTramite(id) {
            console.log('Editar trámite:', id);
            // Implementar redirección o modal
        }

        function verTramite(id) {
            console.log('Ver trámite:', id);
            // Implementar navegación a página de detalle
        }

        function editarRecinto(id) {
            console.log('Editar recinto:', id);
            // Implementar redirección o modal
        }

        function verRecinto(id) {
            console.log('Ver recinto:', id);
            // Implementar navegación a página de detalle
        }

//-----------------------
//----EVENTOS----//
//-----------------------


        // Inicializar en carga
        document.addEventListener('DOMContentLoaded', () => {
            cargarEstadisticas();
            cargarTramites();
            cargarRecintos();
        });