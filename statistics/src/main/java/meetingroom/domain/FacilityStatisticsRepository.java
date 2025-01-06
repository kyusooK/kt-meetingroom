package meetingroom.domain;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import meetingroom.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "facilityStatistics",
    path = "facilityStatistics"
)
public interface FacilityStatisticsRepository
    extends PagingAndSortingRepository<FacilityStatistics, Long> {
        Optional<FacilityStatistics> findByFacilityName(String facilityName);
    }
