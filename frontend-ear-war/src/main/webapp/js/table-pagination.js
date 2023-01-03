$(document).ready(function () {
    $('#table').DataTable({
        scrollX: true,
        language: {
            processing: "Traitement en cours...",
            search: "Buscar&nbsp;:",
            lengthMenu: "Elementos por paginación _MENU_",
            info: "Mostrando del _START_ al _END_ de _TOTAL_ filas",
            infoEmpty: "Affichage de l'&eacute;lement 0 &agrave; 0 sur 0 &eacute;l&eacute;ments",
            infoFiltered: "(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)",
            infoPostFix: "",
            loadingRecords: "Chargement en cours...",
            zeroRecords: "Aucun &eacute;l&eacute;ment &agrave; afficher",
            emptyTable: "No hay datos disponibles para mostrar.",
            paginate: {
                first: "Inicio",
                previous: "Anterior",
                next: "Siguiente",
                last: "Ultimo"
            },
            aria: {
                sortAscending: ": activer pour trier la colonne par ordre croissant",
                sortDescending: ": activer pour trier la colonne par ordre décroissant"
            }
        }
    });
});