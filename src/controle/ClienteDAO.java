package controle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.ClientesBEAN;

public class ClienteDAO {

    private static ClienteDAO instance;

    private ClienteDAO() {
        MySQLDAO.getConnection();
    }

    public static ClienteDAO getInstance() {
        if (instance == null) {
            instance = new ClienteDAO();
        }
        return instance;
    }

    public void update(ClientesBEAN cliente) {
        String query = "UPDATE CLIENTES SET "
                + "nome = ?, "
                + "sobrenome = ?, "
                + "cpf = ?, "
                + "data_nasc = ?, "
                + "telefone = ?, "
                + "rua = ?, "
                + "bairro = ?, "
                + "cidade = ?, "
                + "estado = ?, "
                + "numero = ?, "
                + "sexo = ? "
                + "where id = ?";
        MySQLDAO.executeQuery(query, 
                cliente.getNome(), 
                cliente.getSobrenome(), 
                cliente.getCPF(), 
                cliente.getDataNasc(), 
                cliente.getTelefone(), 
                cliente.getRua(),
                cliente.getBairro(),
                cliente.getCidade(),
                cliente.getEstado(),
                cliente.getNumero(),
                cliente.getSexo(),
                cliente.getId());
    }
    
    public long create(ClientesBEAN cliente) {

        String query = "INSERT INTO `clientes` ("
                + "`nome`, "
                + "`sobrenome`, "
                + "`cpf`, "
                + "`data_nasc`, "
                + "`telefone`, "
                + "`rua`, "
                + "`bairro`, "
                + "`cidade`, "
                + "`estado`, "
                + "`numero`, "
                + "`sexo`) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        return MySQLDAO.executeQuery(query,
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCPF(),
                cliente.getDataNasc(),
                cliente.getTelefone(),
                cliente.getRua(),
                cliente.getBairro(),
                cliente.getCidade(),
                cliente.getEstado(),
                cliente.getNumero(),
                cliente.getSexo());
    }

    public void delete(ClientesBEAN cliente) {
        MySQLDAO.executeQuery("DELETE FROM CLIENTES WHERE id = ?", cliente.getId());
    }

    public void delete(int id) {
        MySQLDAO.executeQuery("DELETE FROM CLIENTES WHERE id = ?", id);
    }

    public void ativa(boolean flag, int id) {
        MySQLDAO.executeQuery("update clientes set ativo = ? where id = ?", flag, id);
    }

    public ArrayList<ClientesBEAN> buscarTodos() {
        return listarTodos("select * from clientes order by id");
    }

    private ArrayList<ClientesBEAN> listarTodos(String query) {
        ArrayList<ClientesBEAN> lista = new ArrayList<>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet(query);
        try {
            while (rs.next()) {
                lista.add(new ClientesBEAN(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("cpf"),
                        rs.getString("data_nasc"),
                        rs.getString("telefone"),
                        rs.getString("rua"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("numero"),
                        rs.getString("sexo"),
                        rs.getObject("ativo", Boolean.class)));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("erro: " + e.getMessage());
        }
        return lista;
    }

    public ClientesBEAN buscar(int id) {
        ClientesBEAN result = null;
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet("SELECT * FROM CLIENTES WHERE id = ?", id);
        try {
            if (rs.next()) {
                result = new ClientesBEAN(rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("cpf"),
                        rs.getString("data_nasc"),
                        rs.getString("telefone"),
                        rs.getString("rua"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("numero"),
                        rs.getString("sexo"),
                        rs.getObject("ativo", Boolean.class));
            }
            rs.close();
        } catch (SQLException e) {
        }
        return result;
    }

    public int buscar(ClientesBEAN cliente) {
        int result = 0;
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet("SELECT * FROM CLIENTES WHERE nome = ? and sobrenome = ?", cliente.getNome(), cliente.getSobrenome());
        try {
            if (rs.next()) {
                result = rs.getInt("id");
            }
            rs.close();
        } catch (SQLException e) {
        }
        return result;
    }

    public ArrayList<ClientesBEAN> buscar(String nome) {
        ArrayList<ClientesBEAN> lista = new ArrayList<>();
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet("select * from clientes where nome like '%" + nome + "%'");
        try {
            while (rs.next()) {
                lista.add(new ClientesBEAN(rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("cpf"),
                        rs.getString("data_nasc"),
                        rs.getString("telefone"),
                        rs.getString("rua"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("numero"),
                        rs.getString("sexo"),
                        rs.getObject("ativo", Boolean.class)));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("erro: " + e.getMessage());
        }
        return lista;
    }

    public Boolean isExist(int id) {
        Boolean result = false;
        ResultSet rs = null;
        rs = MySQLDAO.getResultSet("SELECT * FROM ClIENTES WHERE id = ?", id);
        try {
            if (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
