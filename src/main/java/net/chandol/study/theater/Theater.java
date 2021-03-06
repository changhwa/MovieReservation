package net.chandol.study.theater;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode
public class Theater {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ElementCollection
    @CollectionTable(name = "THEATER_SEAT", joinColumns = @JoinColumn(name = "id"))
    @OrderColumn(name = "SEAT_IDX")
    private List<Seat> seats = new ArrayList<>();

    public Theater(String name, int columnSize, int rowSize) {
        //검증로직 생략
        this.name = name;
        this.seats = seatsGenerator(columnSize, rowSize);
    }

    // 좌석을 생성하여 리스트를 반환합니다.
    private static List<Seat> seatsGenerator(int columnSize, int rowSize) {
        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= rowSize; row++) {
            char rowString = getCharFromNumber(row);
            for (int column = 1; column <= columnSize; column++) {
                seats.add(new Seat(rowString, column));
            }
        }

        return seats;
    }

    // 좌석정보를 반환합니다.
    private static char getCharFromNumber(int i) {
        if (i > 0 && i < 27)
            return (char) (i + 'A' - 1);
        else
            throw new IllegalArgumentException("문자변경 실패");
    }
}

@Repository
interface TheaterRepsitory extends JpaRepository<Theater, Long> {
    Theater findByName(String name);
}

