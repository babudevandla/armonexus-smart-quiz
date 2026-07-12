        </div>
    </div>
</div>

<!-- jQuery (needed by DataTables' jQuery build) -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- Bootstrap 5 JS bundle (includes Popper) — powers the sidebar accordion collapse,
     dismissible alerts, and any other Bootstrap components used across the app -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- DataTables + its Bootstrap 5 styling integration -->
<script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.8/js/dataTables.bootstrap5.min.js"></script>

<script>
    $(document).ready(function () {
        // ---- Paginate every existing data table (10 rows/page by default) ----
        // No individual list JSP needs to change — this one script handles all of them.
        $('table.data-table').each(function () {
            if (!$.fn.DataTable.isDataTable(this)) {
                $(this).addClass('table table-hover align-middle mb-0');
                $(this).DataTable({
                    pageLength: 10,
                    lengthMenu: [5, 10, 25, 50, 100],
                    language: { search: "", searchPlaceholder: "Filter rows..." }
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

        // ---- Mobile sidebar toggle (hamburger button) ----
        var sidebarToggleBtn = document.getElementById('sidebarToggleBtn');
        var sidebarBackdrop = document.getElementById('sidebarBackdrop');
        var body = document.body;

        function toggleSidebar() { body.classList.toggle('sidebar-open'); }
        function closeSidebar() { body.classList.remove('sidebar-open'); }

        if (sidebarToggleBtn) sidebarToggleBtn.addEventListener('click', toggleSidebar);
        if (sidebarBackdrop) sidebarBackdrop.addEventListener('click', closeSidebar);

        // Close the mobile menu automatically after tapping a leaf nav link
        // (not the group-toggle headers themselves, so expanding a section
        // on mobile doesn't instantly close the whole sidebar).
        document.querySelectorAll('.sidebar .nav-link-top, .sidebar .nav-group ul a').forEach(function (link) {
            link.addEventListener('click', function () {
                if (window.innerWidth <= 992) closeSidebar();
            });
        });

        // ---- Highlight the current page's link + auto-expand its group ----
        var currentPath = window.location.pathname;
        var bestMatch = null;
        document.querySelectorAll('.sidebar a[href]').forEach(function (link) {
            var linkPath = link.getAttribute('href');
            if (!linkPath || linkPath === '#') return;
            try {
                var linkUrl = new URL(link.href).pathname;
                if (currentPath === linkUrl || currentPath.startsWith(linkUrl + '/')) {
                    if (!bestMatch || linkUrl.length > bestMatch.pathLength) {
                        bestMatch = { link: link, pathLength: linkUrl.length };
                    }
                }
            } catch (e) { /* ignore malformed hrefs */ }
        });

        if (bestMatch) {
            bestMatch.link.classList.add('active');
            var parentCollapse = bestMatch.link.closest('.collapse');
            if (parentCollapse) {
                new bootstrap.Collapse(parentCollapse, { toggle: true });
                var groupToggle = document.querySelector('a[href="#' + parentCollapse.id + '"]');
                if (groupToggle) groupToggle.setAttribute('aria-expanded', 'true');
            } else if (bestMatch.link.classList.contains('nav-link-top')) {
                bestMatch.link.classList.add('active');
            }
        }

        // Rotate the chevron icon in sync with each group's open/closed state
        document.querySelectorAll('.nav-group-title').forEach(function (toggle) {
            var targetId = toggle.getAttribute('href');
            var target = document.querySelector(targetId);
            if (!target) return;
            target.addEventListener('show.bs.collapse', function () { toggle.classList.add('expanded'); });
            target.addEventListener('hide.bs.collapse', function () { toggle.classList.remove('expanded'); });
        });
    });
</script>
</body>
</html>
