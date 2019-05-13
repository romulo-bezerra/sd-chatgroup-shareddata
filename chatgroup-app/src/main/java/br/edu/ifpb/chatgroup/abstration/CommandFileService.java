package br.edu.ifpb.chatgroup.abstration;

import br.edu.ifpb.chatgroup.model.Message;
import jcifs.smb.SmbFile;

public interface CommandFileService {

    String readFile(SmbFile file);
    void writeFile(SmbFile file, Message message);

}
