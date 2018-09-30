package me.jussilemmetyinen.jussinetwork.bookmarks.persistence.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany
    @JoinTable(name = "roles_privileges", joinColumns =
    @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns =
    @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    private String name;

    public Role() { super(); }

    public Role(final String name) {
        super();
        this.name = name;
    }

    public Long getId() { return id; }

    public void setId(final Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(final String name) {this.name = name; }

    public Collection<User> getUsers() { return users; }

    public void setUsers(final Collection<User> users ) { this.users = users; }

    public Collection<Privilege> getPrivileges() { return privileges; }

    public void setPrivileges(final Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        } if (object == null) {
            return false;
        } if (getClass() != object.getClass()) {
            return false;
        }
        final Role role = (Role) object;
        return role.equals(role.name);
    }

    @Override
    public String toString() {
        return "Role [name=" + name + "]" +
                "[id=" + id + "]";
    }
}
