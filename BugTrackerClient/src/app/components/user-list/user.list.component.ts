import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../model/user";
import {PageEvent} from "@angular/material/paginator";
import {ProjectService} from "../../services/project-service/project.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user.list.component.html',
  styleUrls: ['./user.list.component.css']
})
export class UserListComponent implements OnInit {

  displayedColumns: string[] = ["fullName", "email", "role"];
  @Input() header = 'Users';
  @Input() projectId: number;

  teamMembers = new Array<User>();
  teamMembersCount: number = 10;

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.projectService.getTeamMembersCount(this.projectId).subscribe({
      next: count => {
        this.teamMembersCount = count;
      }
    });

    this.projectService.getTeamMembers(this.projectId, 1).subscribe({
        next: teamMembers => {
          this.teamMembers = teamMembers;
        }
      }
    );


  }

  onPageChange(pageEvent: PageEvent) {
    this.projectService.getTeamMembers(this.projectId, pageEvent.pageIndex + 1);
  }
}
