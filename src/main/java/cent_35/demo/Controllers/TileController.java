package cent_35.demo.Controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;

@RestController
// creado el 14/1/25 para manejar tiles
@RequestMapping("/tiles")
// Este controlador se encarga de la descarga y cacheo de los tiles ( los pedazitos de mapa)
public class TileController {

    private static final String TILE_URL_PATTERN = "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
    private static final String CACHE_DIR = "path/to/cache";
}


// metodo para almacenar los tiles.(en proceso).
//    @GetMapping("/{z}/{x}/{y}")
//    public ResponseEntity<byte[]> getTile (@PathVariable int z, @PathVariable int x, @PathVariable int y) throws IOException
//    { File tileFile = new File(CACHE_DIR + "/" + z + "/" + x + "/" + y + ".png");
//
//        if(!tileFile.exists()) {
//            String url = TILE_URL_PATTERN.replace("{z}",String.valueOf(z))
//                    .replace("{x}", String.valueOf(x))
//                    .replace("{y}", String.valueOf(y))
//                    .replace("{s}", "a"); // para diferentes servidores
//            URL tileURL = new URL(url);
//            tileFile.getParentFile().mkdirs();
//            try (InputStream in = tileURL.openStream(); OutputStream out = new FileOutputStream(tileFile)) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = in.read(buffer)) != -1) {
//                    out.write(buffer, 0, bytesRead);
//                }
//            }
//        }
//        byte[] tileBytes = Files.readAllBytes(tileFile.toPath());
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_TYPE, "image/png")
//                .body(tileBytes);
//    }
//}
