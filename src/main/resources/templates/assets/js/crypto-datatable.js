//______Data-Table

$('#crypto-data-table').DataTable({
    "order": [],
    order: [],
    columnDefs: [{ orderable: false, targets: [0,1,2,3, 4, 5,6,7,8,9,10] }],
    language: {
        searchPlaceholder: 'Search...',
        sSearch: '',
    }
});

// Select2

$('.dataTables_length select').select2({
    minimumResultsForSearch: Infinity
});