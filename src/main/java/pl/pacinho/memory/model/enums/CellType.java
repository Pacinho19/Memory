package pl.pacinho.memory.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CellType {

    EMPTY(null),
    ANDROID("bi bi-android2"),
    APPLE("bi bi-apple"),
    BLUETOOTH("bi bi-bluetooth"),
    CHROME("bi bi-browser-chrome"),
    BUG("bi bi-bug"),
    FINGERPRINT("bi bi-fingerprint"),
    GOOGLE("bi bi-google"),
    LINKEDIN("bi bi-linkedin"),
    QRCODE("bi bi-qr-code"),
    REDDIT("bi bi-reddit");

    @Getter
    private final String iconClass;
}
