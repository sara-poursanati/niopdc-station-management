//______Data-Table

var htmlTag = document.documentElement

var dir = htmlTag.dir

if (dir === "rtl") {
    $('#crypto-data-table').DataTable({
        order: [],
        columnDefs: [{ orderable: false, targets: '_all' }],
        language: {
            searchPlaceholder: 'جستجو...',
            sSearch: '',
        }
    });
} else {
    $('#crypto-data-table').DataTable({
        order: [],
        columnDefs: [{orderable: false, targets: '_all'}],
        language: {
            searchPlaceholder: 'Search...',
            sSearch: '',
        }
    });
}
// Select2

$('.dataTables_length select').select2({
    minimumResultsForSearch: Infinity
});