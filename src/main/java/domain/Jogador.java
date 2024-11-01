package domain;

import java.util.Objects;
import java.util.UUID;

public class Jogador {
    private UUID uuid;
    private String nome;
    private char simbolo;

    public Jogador(String nome, char simbolo) {
        uuid = UUID.randomUUID();
        this.nome = nome;
        this.simbolo = simbolo;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public char getSimbolo() {
        return simbolo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogador jogador = (Jogador) o;
        return Objects.equals(uuid, jogador.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "uuid=" + uuid +
                ", nome='" + nome + '\'' +
                ", simbolo='" + simbolo + '\'' +
                '}';
    }
}
