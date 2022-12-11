package fr.pandonia.uhcapi.utils.jnbt.v2;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class NBTInputStream
        implements Closeable {
    private final DataInputStream is;

    public NBTInputStream(InputStream is) throws IOException {
        this.is = new DataInputStream(is);
    }

    public NamedTag readNamedTag() throws IOException {
        return this.readNamedTag(0);
    }

    private NamedTag readNamedTag(int depth) throws IOException {
        String name;
        int type = this.is.readByte() & 0xFF;
        if (type != 0) {
            int nameLength = this.is.readShort() & 0xFFFF;
            byte[] nameBytes = new byte[nameLength];
            this.is.readFully(nameBytes);
            name = new String(nameBytes, NBTConstants.CHARSET);
        } else {
            name = "";
        }
        return new NamedTag(name, this.readTagPayload(type, depth));
    }

    private Tag readTagPayload(int type, int depth) throws IOException {
        switch (type) {
            case 0: {
                if (depth == 0) {
                    throw new IOException("TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
                }
                return new EndTag();
            }
            case 1: {
                return new ByteTag(this.is.readByte());
            }
            case 2: {
                return new ShortTag(this.is.readShort());
            }
            case 3: {
                return new IntTag(this.is.readInt());
            }
            case 4: {
                return new LongTag(this.is.readLong());
            }
            case 5: {
                return new FloatTag(this.is.readFloat());
            }
            case 6: {
                return new DoubleTag(this.is.readDouble());
            }
            case 7: {
                int k = this.is.readInt();
                byte[] bytes = new byte[k];
                this.is.readFully(bytes);
                return new ByteArrayTag(bytes);
            }
            case 8: {
                short k = this.is.readShort();
                byte[] bytes = new byte[k];
                this.is.readFully(bytes);
                return new StringTag(new String(bytes, NBTConstants.CHARSET));
            }
            case 9: {
                byte childType = this.is.readByte();
                int m = this.is.readInt();
                ArrayList<Tag> tagList = new ArrayList<Tag>();
                for (int i = 0; i < m; ++i) {
                    Tag tag = this.readTagPayload(childType, depth + 1);
                    if (tag instanceof EndTag) {
                        throw new IOException("TAG_End not permitted in a list.");
                    }
                    tagList.add(tag);
                }
                return new ListTag(NBTUtils.getTypeClass(childType), tagList);
            }
            case 10: {
                NamedTag namedTag;
                Tag tag2;
                HashMap<String, Tag> tagMap = new HashMap<String, Tag>();
                while (!((tag2 = (namedTag = this.readNamedTag(depth + 1)).getTag()) instanceof EndTag)) {
                    tagMap.put(namedTag.getName(), tag2);
                }
                return new CompoundTag(tagMap);
            }
            case 11: {
                int length = this.is.readInt();
                int[] data = new int[length];
                for (int j = 0; j < length; ++j) {
                    data[j] = this.is.readInt();
                }
                return new IntArrayTag(data);
            }
        }
        throw new IOException("Invalid tag type: " + type + ".");
    }

    @Override
    public void close() throws IOException {
        this.is.close();
    }
}
