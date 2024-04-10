package Controllers;

import Models.Member;
import Services.ServiceMember;
import java.sql.SQLException;
import java.util.List;

public class MemberController {
    private final ServiceMember serviceMember;

    public MemberController() {
        this.serviceMember = new ServiceMember();
    }

    public void addMember(Member member) {
        try {
            serviceMember.add(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMember(Member member) {
        try {
            serviceMember.delete(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMember(Member member) {
        try {
            serviceMember.update(member);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Member> getAllMembers() {
        try {
            return serviceMember.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
