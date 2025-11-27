export function updateSelectedAddress(text) {
    const el = document.getElementById("selectedAddress");
    if (el) el.textContent = text;
}