package com.lucas.specterdepositos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class Conversao {

	public static String converter(final ItemStack item) {
        try {
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream out = new BukkitObjectOutputStream((OutputStream)stream);
            out.writeInt(1);
            out.writeObject((Object)item);
            out.close();
            return Base64Coder.encodeLines(stream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Não foi possivel salvar os itens", e);
        }
    }
    
    public static ItemStack desconverter(final String item) throws IOException {
        try {
            final ByteArrayInputStream stream = new ByteArrayInputStream(Base64Coder.decodeLines(item));
            final BukkitObjectInputStream input = new BukkitObjectInputStream((InputStream)stream);
            @SuppressWarnings("deprecation")
			ItemStack stacks = new ItemStack(input.readInt());
            stacks = (ItemStack)input.readObject();
            input.close();
            return stacks;
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Não foi possivel retornar os itens", e);
        }
    }
	
}
