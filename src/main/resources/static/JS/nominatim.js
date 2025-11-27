export async function reverseGeocode(lat, lng) {
    const url = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;
    try {
        const res = await fetch(url, {
            headers: { "User-Agent": "TramysApp/1.0" }
        });

        if(!res.ok) return null;
        const data = await res.json();
        return data.display_name || null;
    } catch ( error) {
        return null;
    }
    
}