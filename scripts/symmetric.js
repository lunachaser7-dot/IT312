const crypto = require("crypto");

const algorithm = "aes-256-cbc";
const password = "safe-password-123";
const salt = Buffer.from("1122334455667788", "hex");
const key = crypto.scryptSync(password, salt, 32);

function decrypt(cipherTextHex) {
    const combined = Buffer.from(cipherTextHex, "hex");

    // Extract IV (first 16 bytes) and ciphertext (rest)
    const iv = combined.slice(0, 16);
    const encryptedText = combined.slice(16);

    const decipher = crypto.createDecipheriv(algorithm, key, iv);
    let decrypted = decipher.update(encryptedText, null, "utf8");
    decrypted += decipher.final("utf8");
    return decrypted;
}

const encryptedMessage = "6aadf68f933a46bf09bc9fdb1b82051994f19692e55b15c3f25ba303f5cb75e4";

console.log("Decrypted Message:", decrypt(encryptedMessage));
