        </div>
    </div>
</div>

<!-- jQuery + DataTables: auto-applies search/sort/pagination to every
     table.data-table on the page. No individual list JSP needed to change —
     this one script handles all of them. -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.datatables.net/2.3.8/js/dataTables.js"></script>
<script>
    $(document).ready(function () {
        // Paginate every existing data table (10 rows/page, adjustable via the on-page dropdown)
        $('table.data-table').each(function () {
            if (!$.fn.DataTable.isDataTable(this)) {
                $(this).DataTable({
                    pageLength: 10,
                    lengthMenu: [5, 10, 25, 50, 100],
                    language: { search: "Search:", searchPlaceholder: "Filter rows..." }
                });
            }
        });

        // Wrap DataTables' generated markup so wide tables scroll horizontally
        // on small screens instead of breaking the page layout.
        $('.dataTables_wrapper').each(function () {
            if (!$(this).parent().hasClass('table-scroll-wrapper')) {
                $(this).wrap('<div class="table-scroll-wrapper"></div>');
            }
        });

        // Sidebar toggle (hamburger button) — works for the mobile
        // off-canvas menu and as a collapse toggle on desktop.
        var sidebarToggleBtn = document.getElementById('sidebarToggleBtn');
        var sidebarBackdrop = document.getElementById('sidebarBackdrop');
        var body = document.body;

        function toggleSidebar() {
            body.classList.toggle('sidebar-open');
        }
        function closeSidebar() {
            body.classList.remove('sidebar-open');
        }

        if (sidebarToggleBtn) sidebarToggleBtn.addEventListener('click', toggleSidebar);
        if (sidebarBackdrop) sidebarBackdrop.addEventListener('click', closeSidebar);

        // Close the mobile menu automatically after tapping a nav link
        document.querySelectorAll('.sidebar a').forEach(function (link) {
            link.addEventListener('click', function () {
                if (window.innerWidth <= 768) closeSidebar();
            });
        });
    });
</script>
</body>
</html>
