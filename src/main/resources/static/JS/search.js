export async function buscarCoordenadas(query) {
    const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(query)}`;
    const res = await fetch ( url, {
        headers: { "User-Agent": "TramysApp/1.0"}
    });

    return await res.json();
}