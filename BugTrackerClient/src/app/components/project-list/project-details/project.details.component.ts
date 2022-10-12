import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import { Ticket } from 'src/app/model/ticket';
import { Project } from '../../../model/project';
import { ProjectService } from '../../../services/project-service/project.service';

@Component({
  selector: 'app-project-details',
  templateUrl: './project.details.component.html',
  styleUrls: ['./project.details.component.css'],
})
export class ProjectDetailsComponent implements OnInit, OnDestroy {
  id: number;
  private paramsSubscription: Subscription;
  project$: Observable<Project>;
  tickets = new Array<Ticket>();

  constructor(private route: ActivatedRoute, private projectService: ProjectService) {
  }

  ngOnInit(): void {
    this.paramsSubscription = this.route.params.subscribe((params) => {
      this.id = +params['projectId'];
      this.loadProject();
    });
  }

  loadProject(): void {
    if (isNaN(this.id)) {
      console.log(this.id);
      return;
    }
    this.project$ = this.projectService.findById(this.id);
  }

  ngOnDestroy(): void {
    if (this.paramsSubscription != null) {
      this.paramsSubscription.unsubscribe();
    }
  }

}
