package engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SolutionRepository extends PagingAndSortingRepository<Solution, Integer> {
    Page<Solution> findAllByCompletedBy(String completedBy, Pageable pageable);
}
