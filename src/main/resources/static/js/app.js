document.addEventListener('DOMContentLoaded', () => {
    lucide.createIcons();

    // 1. Live Search: Filter (Hide) + Glow
    const searchBar = document.getElementById('searchBar');
    if (searchBar) {
        searchBar.addEventListener('input', (e) => {
            const query = e.target.value.toLowerCase().trim();
            const rows = document.querySelectorAll('tbody tr');

            rows.forEach(row => {
                const rollNoText = row.querySelector('.roll-no-cell').innerText.toLowerCase();
                
                if (query === "") {
                    row.classList.remove('row-hidden', 'row-glow');
                } else if (rollNoText.includes(query)) {
                    row.classList.remove('row-hidden');
                    row.classList.add('row-glow');
                } else {
                    row.classList.add('row-hidden');
                    row.classList.remove('row-glow');
                }
            });
        });
    }

    // 2. Clipboard Copy Logic
    const copyItems = document.querySelectorAll('.copy-trigger');
    copyItems.forEach(item => {
        item.addEventListener('click', function() {
            const val = this.getAttribute('data-ip');
            navigator.clipboard.writeText(val).then(() => {
                const originalColor = this.style.color;
                this.style.color = "#22c55e";
                setTimeout(() => { this.style.color = originalColor; }, 800);
            });
        });
    });

    // 3. NEW: Delete Confirmation
    const deleteForms = document.querySelectorAll('.delete-form');
    deleteForms.forEach(form => {
        form.addEventListener('submit', (e) => {
            if (!confirm('Are you sure you want to delete this log? This action cannot be undone.')) {
                e.preventDefault();
            }
        });
    });

    // 4. Form Submission Success Pop (For index page)
    const mainForm = document.getElementById('entryForm');
    if (mainForm) {
        mainForm.addEventListener('submit', () => {
            const btn = mainForm.querySelector('.btn');
            btn.style.background = "#22c55e";
            btn.innerHTML = 'Success!';
        });
    }
});