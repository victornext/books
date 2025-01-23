package com.next.infod.exceptions;

import lombok.Getter;

public class CampoInvalido extends RuntimeException {
    @Getter
    public String campo;

    public CampoInvalido(String campo, String mensagem){
      super(mensagem);
      this.campo =  campo;
    }

}
