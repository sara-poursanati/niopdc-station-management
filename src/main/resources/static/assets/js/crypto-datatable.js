//______Data-Table

$('#crypto-data-table').DataTable({
    order: [],
    columnDefs: [{ orderable: false, targets: '_all' }],
    language: {
        searchPlaceholder: 'Search...',
        sSearch: '',
    }
});

// Select2

$('.dataTables_length select').select2({
    minimumResultsForSearch: Infinity
});