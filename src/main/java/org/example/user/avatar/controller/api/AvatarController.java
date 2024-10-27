package org.example.user.avatar.controller.api;

import java.io.InputStream;
import java.util.UUID;

public interface AvatarController {
    byte[] getAvatar(UUID id);
    void putAvatar(UUID id, InputStream is);
    void deleteAvatar(UUID id);
}
