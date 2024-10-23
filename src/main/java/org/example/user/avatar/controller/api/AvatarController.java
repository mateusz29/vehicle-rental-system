package org.example.user.avatar.controller.api;

import java.io.InputStream;
import java.util.UUID;

public interface AvatarController {
    byte[] getAvatar(UUID uuid);
    void putAvatar(UUID uuid, InputStream is);
    void deleteAvatar(UUID uuid);
}
